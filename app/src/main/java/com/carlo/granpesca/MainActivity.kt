package com.carlo.granpesca

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import java.io.File
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.DocumentsContract
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private var wantAny = false
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        val cb = fileCallback
        fileCallback = null
        cb?.onReceiveValue(if (uri != null) arrayOf(uri) else null)
    }

    // 0.14.1: cartella dei backup scelta dal giocatore (sopravvive alla disinstallazione; l'app la può sempre sovrascrivere e pulire)
    private class Child(val id: String, val name: String, val lm: Long, val dir: Boolean)
    private class Cand(val name: String, val lm: Long, val prev: Boolean, val open: () -> ByteArray?)
    private val gpPrefs by lazy { getSharedPreferences("gp_files", MODE_PRIVATE) }
    private val pickTree = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        var res = ""
        if (uri != null) {
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                val rootId = DocumentsContract.getTreeDocumentId(uri)
                val rootDoc = DocumentsContract.buildDocumentUriUsingTree(uri, rootId)
                var dirId: String? = null
                if (docName(rootDoc) == "GranPesca") dirId = rootId
                else {
                    dirId = listChildren(uri, rootId).firstOrNull { it.dir && it.name == "GranPesca" }?.id
                    if (dirId == null) {
                        val d = DocumentsContract.createDocument(contentResolver, rootDoc, DocumentsContract.Document.MIME_TYPE_DIR, "GranPesca")
                        if (d != null) dirId = DocumentsContract.getDocumentId(d)
                    }
                }
                if (dirId != null) { gpPrefs.edit().putString("tree", uri.toString()).putString("dir", dirId).apply(); res = folderLabel() }
            } catch (e: Exception) { }
        }
        try { webView.evaluateJavascript("window.gpFolder140&&window.gpFolder140(" + org.json.JSONObject.quote(res) + ")", null) } catch (e: Exception) { }
    }
    private fun listChildren(tree: Uri, parentId: String): List<Child> {
        val out = ArrayList<Child>()
        val u = DocumentsContract.buildChildDocumentsUriUsingTree(tree, parentId)
        contentResolver.query(u, arrayOf(DocumentsContract.Document.COLUMN_DOCUMENT_ID, DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_LAST_MODIFIED, DocumentsContract.Document.COLUMN_MIME_TYPE), null, null, null)?.use { c ->
            while (c.moveToNext()) out.add(Child(c.getString(0), c.getString(1) ?: "", c.getLong(2), c.getString(3) == DocumentsContract.Document.MIME_TYPE_DIR))
        }
        return out
    }
    private fun docName(doc: Uri): String = try {
        contentResolver.query(doc, arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME), null, null, null)?.use { if (it.moveToFirst()) it.getString(0) ?: "" else "" } ?: ""
    } catch (e: Exception) { "" }
    // cartella scelta e ancora valida (permesso presente, cartella non cancellata)
    private fun treeAndDir(): Pair<Uri, String>? {
        return try {
            val t = gpPrefs.getString("tree", null) ?: return null
            val d = gpPrefs.getString("dir", null) ?: return null
            val tree = Uri.parse(t)
            if (contentResolver.persistedUriPermissions.none { it.uri == tree && it.isWritePermission }) return null
            if (docName(DocumentsContract.buildDocumentUriUsingTree(tree, d)).isEmpty()) return null
            Pair(tree, d)
        } catch (e: Exception) { null }
    }
    private fun folderLabel(): String {
        val d = gpPrefs.getString("dir", null) ?: return ""
        val l = d.substringAfter(':', d)
        return if (l.isEmpty()) "GranPesca" else l
    }
    private fun isGpName(n: String) = n.startsWith("GranPesca") && n.endsWith(".gp")
    private fun isPrevName(n: String) = n.startsWith("GranPesca-precedente")

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Schermo intero: nasconde barra di stato e di navigazione
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }

        webView = WebView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(0xFF06324F.toInt())
            overScrollMode = WebView.OVER_SCROLL_NEVER
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            isLongClickable = false
            setOnLongClickListener { true }
        }
        val root = FrameLayout(this).apply { setBackgroundColor(0xFF06324F.toInt()); addView(webView) }
        setContentView(root)
        // bordo a bordo (Android 15/16): evita notch e angoli arrotondati
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val c = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            v.setPadding(c.left, c.top, c.right, c.bottom)
            insets
        }

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            mediaPlaybackRequiresUserGesture = false
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
        }
        // dopo un aggiornamento si svuota la cache del WebView (non i dati di gioco)
        try {
            val prefs = getSharedPreferences("gp_boot", MODE_PRIVATE)
            val pi = packageManager.getPackageInfo(packageName, 0)
            val vc = if (Build.VERSION.SDK_INT >= 28) pi.longVersionCode else pi.versionCode.toLong()
            if (prefs.getLong("vc", -1L) != vc) { webView.clearCache(true); prefs.edit().putLong("vc", vc).apply() }
        } catch (e: Exception) { }
        // se l'app si è chiusa per un errore, al riavvio il gioco mostra la schermata di recupero
        val crashFlag = File(filesDir, "gp_crash.flag")
        val rescue = crashFlag.exists()
        try { crashFlag.delete() } catch (e: Exception) { }
        val prevHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            try { File(filesDir, "gp_crash.flag").writeText(System.currentTimeMillis().toString()) } catch (x: Exception) { }
            prevHandler?.uncaughtException(t, e)
        }
        // i link esterni (pagina della privacy, email) si aprono nel browser o nell'app di posta, non dentro il gioco
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val uri = request?.url ?: return false
                if (uri.scheme == "http" || uri.scheme == "https" || uri.scheme == "mailto") {
                    try { startActivity(Intent(Intent.ACTION_VIEW, uri)) } catch (e: Exception) { }
                    return true
                }
                return false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                view: WebView?,
                callback: ValueCallback<Array<Uri>>?,
                params: FileChooserParams?
            ): Boolean {
                fileCallback?.onReceiveValue(null)
                fileCallback = callback
                try {
                    // immagini dalla galleria (nessun permesso fotocamera); qualsiasi file se serve un .gp
                    val any = params?.acceptTypes?.any { it.contains("gp") || it.contains("octet") || it.contains("text") } == true
                    pickImage.launch(if (any) "*/*" else "image/*")
                } catch (e: Exception) {
                    fileCallback?.onReceiveValue(null)
                    fileCallback = null
                }
                return true
            }
        }

        // condivisione della foto ricordo con il menu di sistema: il file resta nella cache dell'app, nessun permesso richiesto
        webView.addJavascriptInterface(ShareBridge(), "GPAndroid")
        webView.loadUrl("file:///android_asset/index.html" + (if (rescue) "#rescue" else ""))

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                webView.evaluateJavascript(
                    "(window.handleBack ? window.handleBack() : false)"
                ) { result ->
                    if (result != "true") finish()
                }
            }
        })
    }

    inner class ShareBridge {
        // copia interna della partita: sopravvive allo svuotamento dei dati del WebView ed entra nel backup Android
        // 0.14.1: copia interna a due slot alternati (A/B). Ogni slot ha un'intestazione «progressivo|crc32|lunghezza»: si scrive sempre nello slot
        // che NON contiene l'ultima copia valida, quindi una scrittura interrotta non può rovinare entrambe. La lettura usa la più recente valida
        private fun slotFile(i: Int) = File(filesDir, if (i == 0) "gp_mirror_a.dat" else "gp_mirror_b.dat")
        private fun crcOf(t: String): String = java.util.zip.CRC32().apply { update(t.toByteArray(Charsets.UTF_8)) }.value.toString(16)
        private fun readSlot(f: File): Pair<Long, String>? {
            return try {
                if (!f.exists()) return null
                val t = f.readText(); val nl = t.indexOf('\n'); if (nl < 0) return null
                val h = t.substring(0, nl).split('|'); if (h.size != 3) return null
                val body = t.substring(nl + 1)
                if (body.length.toString() != h[2] || crcOf(body) != h[1]) return null
                Pair(h[0].toLong(), body)
            } catch (e: Exception) { null }
        }
        @JavascriptInterface
        @Synchronized
        fun mirrorWrite(json: String) {
            try {
                if (json.length < 3) return
                val a = readSlot(slotFile(0)); val b = readSlot(slotFile(1))
                val seqA = a?.first ?: 0L; val seqB = b?.first ?: 0L
                val newest = if (a != null && (b == null || seqA >= seqB)) a else b
                val idx = if (a != null && (b == null || seqA >= seqB)) 1 else 0
                // una copia oraria separata, per tornare indietro di un po' se l'ultima fosse sbagliata
                val prev = File(filesDir, "gp_mirror.prev.json")
                if (newest != null && (!prev.exists() || System.currentTimeMillis() - prev.lastModified() > 3600_000L)) prev.writeText(newest.second)
                val f = slotFile(idx); val tmp = File(filesDir, f.name + ".tmp")
                tmp.writeText((maxOf(seqA, seqB) + 1).toString() + "|" + crcOf(json) + "|" + json.length + "\n" + json)
                if (readSlot(tmp) == null) { tmp.delete(); return }          // controllo prima di sostituire
                if (!tmp.renameTo(f)) { f.writeText(tmp.readText()); tmp.delete() }
            } catch (e: Exception) { }
        }

        @JavascriptInterface
        @Synchronized
        fun mirrorRead(): String {
            return try {
                val a = readSlot(slotFile(0)); val b = readSlot(slotFile(1))
                val best = if (a != null && (b == null || a.first >= b.first)) a else b
                if (best != null) return best.second
                val cur = File(filesDir, "gp_mirror.json")      // formato delle versioni precedenti
                val prev = File(filesDir, "gp_mirror.prev.json")
                if (cur.exists() && cur.length() > 2) cur.readText()
                else if (prev.exists()) prev.readText() else ""
            } catch (e: Exception) { "" }
        }

        // salva un file in Download (non in galleria). 1 = Download, 2 = cartella dell'app, 0 = errore
        @JavascriptInterface
        fun saveDownload(name: String, mime: String, b64: String): Int {
            return try {
                val safe = name.replace(Regex("[^A-Za-z0-9._-]"), "_").take(80).ifEmpty { "GranPesca.bin" }
                val bytes = Base64.decode(b64, Base64.DEFAULT)
                if (isGpName(safe)) return saveGp(isPrevName(safe), bytes)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val res = contentResolver
                    val coll = MediaStore.Downloads.EXTERNAL_CONTENT_URI
                    var uri: Uri? = null
                    res.query(coll, arrayOf(MediaStore.Downloads._ID),
                        "${MediaStore.Downloads.DISPLAY_NAME}=? AND ${MediaStore.Downloads.RELATIVE_PATH} LIKE ?",
                        arrayOf(safe, Environment.DIRECTORY_DOWNLOADS + "%"), null)?.use { c ->
                        if (c.moveToFirst()) uri = android.content.ContentUris.withAppendedId(coll, c.getLong(0))
                    }
                    if (uri == null) {
                        val cv = ContentValues().apply {
                            put(MediaStore.Downloads.DISPLAY_NAME, safe)
                            put(MediaStore.Downloads.MIME_TYPE, mime)
                            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                        }
                        uri = res.insert(coll, cv)
                    }
                    val u = uri ?: return 0
                    res.openOutputStream(u, "wt")?.use { it.write(bytes) } ?: return 0
                    1
                } else {
                    val dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: filesDir
                    dir.mkdirs(); File(dir, safe).writeBytes(bytes); 2
                }
            } catch (e: Exception) { 0 }
        }
        // rilegge da Download il file scritto da saveDownload (stesso nome): serve al controllo del file .gp. Stringa vuota se non c'è
        @JavascriptInterface
        fun readDownload(name: String): String {
            return try {
                val safe = name.replace(Regex("[^A-Za-z0-9._-]"), "_").take(80)
                if (safe.isEmpty()) return ""
                if (isGpName(safe)) { // l'ultimo file del suo tipo (corrente o precedente)
                    val c = buildCands().firstOrNull { it.prev == isPrevName(safe) } ?: return ""
                    return String(c.open() ?: return "", Charsets.UTF_8)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val res = contentResolver
                    val coll = MediaStore.Downloads.EXTERNAL_CONTENT_URI
                    var uri: Uri? = null
                    res.query(coll, arrayOf(MediaStore.Downloads._ID),
                        "${MediaStore.Downloads.DISPLAY_NAME}=? AND ${MediaStore.Downloads.RELATIVE_PATH} LIKE ?",
                        arrayOf(safe, Environment.DIRECTORY_DOWNLOADS + "%"), null)?.use { c ->
                        if (c.moveToFirst()) uri = android.content.ContentUris.withAppendedId(coll, c.getLong(0))
                    }
                    val u = uri ?: return ""
                    res.openInputStream(u)?.use { String(it.readBytes(), Charsets.UTF_8) } ?: ""
                } else {
                    val dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: filesDir
                    val f = File(dir, safe)
                    if (f.exists()) f.readText() else ""
                }
            } catch (e: Exception) { "" }
        }
        // 0.14.1: un solo file «GranPesca.gp» (e uno «GranPesca-precedente.gp»), sempre sovrascritto. Se il giocatore ha scelto una cartella
        // si scrive lì (scrittura sicura: file temporaneo, controllo, poi sostituzione); altrimenti in Download
        private fun saveGp(prev: Boolean, bytes: ByteArray): Int {
            val target = if (prev) "GranPesca-precedente.gp" else "GranPesca.gp"
            val td = treeAndDir()
            if (td != null) { try { if (saveSaf(td.first, td.second, target, prev, bytes)) return 1 } catch (e: Exception) { } }
            return saveGpDownload(target, prev, bytes)
        }
        private fun saveSaf(tree: Uri, dirId: String, target: String, prev: Boolean, bytes: ByteArray): Boolean {
            val parent = DocumentsContract.buildDocumentUriUsingTree(tree, dirId)
            fun del(id: String) { try { DocumentsContract.deleteDocument(contentResolver, DocumentsContract.buildDocumentUriUsingTree(tree, id)) } catch (e: Exception) { } }
            val tmpName = target.removeSuffix(".gp") + "-nuovo.gp"
            listChildren(tree, dirId).filter { !it.dir && it.name == tmpName }.forEach { del(it.id) }
            val tmp = DocumentsContract.createDocument(contentResolver, parent, "application/octet-stream", tmpName) ?: return false
            contentResolver.openOutputStream(tmp, "wt")?.use { it.write(bytes) } ?: return false
            val back = contentResolver.openInputStream(tmp)?.use { it.readBytes() }
            if (back == null || !back.contentEquals(bytes)) { try { DocumentsContract.deleteDocument(contentResolver, tmp) } catch (e: Exception) { }; return false }
            listChildren(tree, dirId).filter { !it.dir && it.name == target }.forEach { del(it.id) }
            val renamed = try { DocumentsContract.renameDocument(contentResolver, tmp, target) } catch (e: Exception) { null }
            if (renamed == null) {
                val t2 = DocumentsContract.createDocument(contentResolver, parent, "application/octet-stream", target)
                if (t2 != null) {
                    val ok = try { contentResolver.openOutputStream(t2, "wt")?.use { it.write(bytes) } != null } catch (e: Exception) { false }
                    if (ok) { try { DocumentsContract.deleteDocument(contentResolver, tmp) } catch (e: Exception) { } }
                }
            }
            // pulizia: via tutti gli altri file GranPesca*.gp dello stesso tipo (copie «(1)», vecchi file con data e ora)
            listChildren(tree, dirId).filter { !it.dir && isGpName(it.name) && isPrevName(it.name) == prev && it.name != target }.forEach { del(it.id) }
            return true
        }
        // file GranPesca*.gp creati da questa installazione in Download (Android non ci lascia vedere quelli di altre installazioni), il più recente per primo
        private fun ownGp(prev: Boolean): List<Pair<Uri, Long>> {
            val out = ArrayList<Pair<Uri, Long>>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val coll = MediaStore.Downloads.EXTERNAL_CONTENT_URI
                val sel = "${MediaStore.Downloads.DISPLAY_NAME} LIKE ? AND ${MediaStore.Downloads.DISPLAY_NAME} LIKE ? AND ${MediaStore.Downloads.DISPLAY_NAME} " +
                    (if (prev) "LIKE" else "NOT LIKE") + " ? AND ${MediaStore.Downloads.RELATIVE_PATH} LIKE ?"
                try {
                    contentResolver.query(coll, arrayOf(MediaStore.Downloads._ID, MediaStore.Downloads.DATE_MODIFIED), sel,
                        arrayOf("GranPesca%", "%.gp", "GranPesca-precedente%", Environment.DIRECTORY_DOWNLOADS + "%"),
                        "${MediaStore.Downloads.DATE_MODIFIED} DESC, ${MediaStore.Downloads._ID} DESC")?.use { c ->
                        while (c.moveToNext()) out.add(Pair(android.content.ContentUris.withAppendedId(coll, c.getLong(0)), c.getLong(1) * 1000L))
                    }
                } catch (e: Exception) { }
            }
            return out
        }
        private fun saveGpDownload(target: String, prev: Boolean, bytes: ByteArray): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val own = ownGp(prev)
                var uri: Uri? = own.firstOrNull()?.first
                if (uri == null) {
                    val cv = ContentValues().apply {
                        put(MediaStore.Downloads.DISPLAY_NAME, target)
                        put(MediaStore.Downloads.MIME_TYPE, "application/octet-stream")
                        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }
                    uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv)
                }
                val u = uri ?: return 0
                contentResolver.openOutputStream(u, "wt")?.use { it.write(bytes) } ?: return 0
                own.drop(1).forEach { try { contentResolver.delete(it.first, null, null) } catch (e: Exception) { } }
                return 1
            }
            val dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: filesDir
            dir.mkdirs()
            val f = File(dir, target); val t = File(dir, "$target.tmp")
            t.writeBytes(bytes)
            if (!t.renameTo(f)) { f.writeBytes(bytes); t.delete() }
            (dir.listFiles() ?: arrayOf()).filter { isGpName(it.name) && isPrevName(it.name) == prev && it.name != target }.forEach { try { it.delete() } catch (e: Exception) { } }
            return 2
        }
        // tutti i backup leggibili: prima i più recenti del tipo corrente, poi quelli «precedente»
        private fun buildCands(): List<Cand> {
            val out = ArrayList<Cand>()
            try {
                val td = treeAndDir()
                if (td != null) listChildren(td.first, td.second).filter { !it.dir && isGpName(it.name) }.forEach { ch ->
                    out.add(Cand(ch.name, ch.lm, isPrevName(ch.name)) { contentResolver.openInputStream(DocumentsContract.buildDocumentUriUsingTree(td.first, ch.id))?.use { it.readBytes() } })
                }
            } catch (e: Exception) { }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                for (prev in listOf(false, true)) ownGp(prev).forEach { (u, lm) -> out.add(Cand("dl", lm, prev) { contentResolver.openInputStream(u)?.use { it.readBytes() } }) }
            } else {
                val dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: filesDir
                (dir.listFiles() ?: arrayOf()).filter { isGpName(it.name) }.forEach { f -> out.add(Cand(f.name, f.lastModified(), isPrevName(f.name)) { f.readBytes() }) }
            }
            return out.sortedWith(compareBy<Cand> { it.prev }.thenByDescending { it.lm })
        }
        private var cands: List<Cand> = emptyList()
        @JavascriptInterface
        fun gpCount(): Int { cands = buildCands(); return cands.size }
        @JavascriptInterface
        fun readGpAt(i: Int): String = try { cands.getOrNull(i)?.open()?.let { String(it, Charsets.UTF_8) } ?: "" } catch (e: Exception) { "" }
        @JavascriptInterface
        fun readLatestDownload(): String {
            for (c in buildCands()) { val s = try { c.open()?.let { String(it, Charsets.UTF_8) } ?: "" } catch (e: Exception) { "" }; if (s.isNotEmpty()) return s }
            return ""
        }
        // cartella dei backup
        @JavascriptInterface
        fun gpFolder(): String = if (treeAndDir() != null) folderLabel() else ""
        @JavascriptInterface
        fun gpPickFolder() {
            runOnUiThread {
                try {
                    val init = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) DocumentsContract.buildRootUri("com.android.externalstorage.documents", "primary") else null
                    pickTree.launch(init)
                } catch (e: Exception) { try { webView.evaluateJavascript("window.gpFolder140&&window.gpFolder140('')", null) } catch (x: Exception) { } }
            }
        }
        // cancella i file GranPesca*.gp che questa installazione ha creato in Download (dopo che la cartella nuova è stata scritta). Restituisce quanti
        @JavascriptInterface
        fun cleanOldDownloads(): Int {
            var n = 0
            for (prev in listOf(false, true)) ownGp(prev).forEach { try { if (contentResolver.delete(it.first, null, null) > 0) n++ } catch (e: Exception) { } }
            return n
        }
        @JavascriptInterface
        fun shareImage(b64: String, name: String, text: String) {
            runOnUiThread {
                try {
                    val dir = File(cacheDir, "shared").apply { mkdirs() }
                    dir.listFiles()?.forEach { it.delete() }
                    val safe = name.replace(Regex("[^A-Za-z0-9._-]"), "_").take(80).ifEmpty { "GranPesca.jpg" }
                    val f = File(dir, safe)
                    f.writeBytes(Base64.decode(b64, Base64.DEFAULT))
                    val uri = FileProvider.getUriForFile(this@MainActivity, "$packageName.photos", f)
                    val send = Intent(Intent.ACTION_SEND).apply {
                        type = "image/jpeg"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        putExtra(Intent.EXTRA_TEXT, text)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    startActivity(Intent.createChooser(send, null))
                } catch (e: Exception) { }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        webView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
