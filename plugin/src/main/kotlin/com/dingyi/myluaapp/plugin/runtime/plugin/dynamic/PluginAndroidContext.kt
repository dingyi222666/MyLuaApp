package com.dingyi.myluaapp.plugin.runtime.plugin.dynamic

import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.UserHandle
import android.view.Display
import com.dingyi.myluaapp.common.ktx.convertObject
import com.dingyi.myluaapp.common.ktx.getJavaClass
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class PluginAndroidContext(
    private val pluginPath: File,
    private val wrapperContext: Context
):Context() {


    private var defaultAssetManager: AssetManager
    private lateinit var defaultResources: Resources

    init {
        defaultAssetManager = getJavaClass<AssetManager>()
            .getDeclaredConstructor()
            .apply {
                isAccessible = true
            }
            .newInstance()
            .convertObject()

        getJavaClass<AssetManager>()
            .getDeclaredMethod("addAssetsPath", getJavaClass<String>())
            .apply {
                isAccessible = true
            }
            .invoke(defaultAssetManager, pluginPath.path)


        defaultResources = Resources(defaultAssetManager,
        wrapperContext.resources.displayMetrics,wrapperContext.resources.configuration)


    }



    override fun getAssets(): AssetManager {
        return defaultAssetManager
    }

    override fun getResources(): Resources {
       return defaultResources
    }

    override fun getPackageManager(): PackageManager {
       error("This method is not supported")
    }

    override fun getContentResolver(): ContentResolver {
        error("This method is not supported")
    }

    override fun getMainLooper(): Looper {
        return wrapperContext.mainLooper
    }

    override fun getApplicationContext(): Context {
       return wrapperContext.applicationContext
    }

    override fun setTheme(resid: Int) {
        error("This method is not supported")
    }

    override fun getTheme(): Resources.Theme {
        error("This method is not supported")
    }

    override fun getClassLoader(): ClassLoader {
        error("This method is not supported")
    }

    override fun getPackageName(): String {
        error("This method is not supported")
    }

    override fun getApplicationInfo(): ApplicationInfo {
        error("This method is not supported")
    }

    override fun getPackageResourcePath(): String {
        error("This method is not supported")
    }

    override fun getPackageCodePath(): String {
        error("This method is not supported")
    }

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences {
        error("This method is not supported")
    }

    override fun moveSharedPreferencesFrom(sourceContext: Context?, name: String?): Boolean {
        error("This method is not supported")
    }

    override fun deleteSharedPreferences(name: String?): Boolean {
        error("This method is not supported")
    }

    override fun openFileInput(name: String?): FileInputStream {
        error("This method is not supported")
    }

    override fun openFileOutput(name: String?, mode: Int): FileOutputStream {
        error("This method is not supported")
    }

    override fun deleteFile(name: String?): Boolean {
        error("This method is not supported")
    }

    override fun getFileStreamPath(name: String?): File {
        error("This method is not supported")
    }

    override fun getDataDir(): File {
        error("This method is not supported")
    }

    override fun getFilesDir(): File {
        error("This method is not supported")
    }

    override fun getNoBackupFilesDir(): File {
        error("This method is not supported")
    }

    override fun getExternalFilesDir(type: String?): File? {
        error("This method is not supported")
    }

    override fun getExternalFilesDirs(type: String?): Array<File> {
        error("This method is not supported")
    }

    override fun getObbDir(): File {
        error("This method is not supported")
    }

    override fun getObbDirs(): Array<File> {
        error("This method is not supported")
    }

    override fun getCacheDir(): File {
        error("This method is not supported")
    }

    override fun getCodeCacheDir(): File {
        error("This method is not supported")
    }

    override fun getExternalCacheDir(): File? {
        error("This method is not supported")
    }

    override fun getExternalCacheDirs(): Array<File> {
        error("This method is not supported")
    }

    override fun getExternalMediaDirs(): Array<File> {
        error("This method is not supported")
    }

    override fun fileList(): Array<String> {
        error("This method is not supported")
    }

    override fun getDir(name: String?, mode: Int): File {
        error("This method is not supported")
    }

    override fun openOrCreateDatabase(
        name: String?,
        mode: Int,
        factory: SQLiteDatabase.CursorFactory?
    ): SQLiteDatabase {
        error("This method is not supported")
    }

    override fun openOrCreateDatabase(
        name: String?,
        mode: Int,
        factory: SQLiteDatabase.CursorFactory?,
        errorHandler: DatabaseErrorHandler?
    ): SQLiteDatabase {
        error("This method is not supported")
    }

    override fun moveDatabaseFrom(sourceContext: Context?, name: String?): Boolean {
        error("This method is not supported")
    }

    override fun deleteDatabase(name: String?): Boolean {
        error("This method is not supported")
    }

    override fun getDatabasePath(name: String?): File {
        error("This method is not supported")
    }

    override fun databaseList(): Array<String> {
        error("This method is not supported")
    }

    override fun getWallpaper(): Drawable {
        error("This method is not supported")
    }

    override fun peekWallpaper(): Drawable {
        error("This method is not supported")
    }

    override fun getWallpaperDesiredMinimumWidth(): Int {
        error("This method is not supported")
    }

    override fun getWallpaperDesiredMinimumHeight(): Int {
        error("This method is not supported")
    }

    override fun setWallpaper(bitmap: Bitmap?) {
        error("This method is not supported")
    }

    override fun setWallpaper(data: InputStream?) {
        error("This method is not supported")
    }

    override fun clearWallpaper() {
        error("This method is not supported")
    }

    override fun startActivity(intent: Intent?) {
        error("This method is not supported")
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        error("This method is not supported")
    }

    override fun startActivities(intents: Array<out Intent>?) {
        error("This method is not supported")
    }

    override fun startActivities(intents: Array<out Intent>?, options: Bundle?) {
        error("This method is not supported")
    }

    override fun startIntentSender(
        intent: IntentSender?,
        fillInIntent: Intent?,
        flagsMask: Int,
        flagsValues: Int,
        extraFlags: Int
    ) {
        error("This method is not supported")
    }

    override fun startIntentSender(
        intent: IntentSender?,
        fillInIntent: Intent?,
        flagsMask: Int,
        flagsValues: Int,
        extraFlags: Int,
        options: Bundle?
    ) {
        error("This method is not supported")
    }

    override fun sendBroadcast(intent: Intent?) {
        error("This method is not supported")
    }

    override fun sendBroadcast(intent: Intent?, receiverPermission: String?) {
        error("This method is not supported")
    }

    override fun sendOrderedBroadcast(intent: Intent?, receiverPermission: String?) {
        error("This method is not supported")
    }

    override fun sendOrderedBroadcast(
        intent: Intent,
        receiverPermission: String?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        error("This method is not supported")
    }

    override fun sendBroadcastAsUser(intent: Intent?, user: UserHandle?) {
        error("This method is not supported")
    }

    override fun sendBroadcastAsUser(
        intent: Intent?,
        user: UserHandle?,
        receiverPermission: String?
    ) {
        error("This method is not supported")
    }

    override fun sendOrderedBroadcastAsUser(
        intent: Intent?,
        user: UserHandle?,
        receiverPermission: String?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        error("This method is not supported")
    }

    override fun sendStickyBroadcast(intent: Intent?) {
        error("This method is not supported")
    }

    override fun sendStickyOrderedBroadcast(
        intent: Intent?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        error("This method is not supported")
    }

    override fun removeStickyBroadcast(intent: Intent?) {
        error("This method is not supported")
    }

    override fun sendStickyBroadcastAsUser(intent: Intent?, user: UserHandle?) {
        error("This method is not supported")
    }

    override fun sendStickyOrderedBroadcastAsUser(
        intent: Intent?,
        user: UserHandle?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        error("This method is not supported")
    }

    override fun removeStickyBroadcastAsUser(intent: Intent?, user: UserHandle?) {
        error("This method is not supported")
    }

    override fun registerReceiver(receiver: BroadcastReceiver?, filter: IntentFilter?): Intent? {
        error("This method is not supported")
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        flags: Int
    ): Intent? {
        error("This method is not supported")
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        broadcastPermission: String?,
        scheduler: Handler?
    ): Intent? {
        error("This method is not supported")
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        broadcastPermission: String?,
        scheduler: Handler?,
        flags: Int
    ): Intent? {
        error("This method is not supported")
    }

    override fun unregisterReceiver(receiver: BroadcastReceiver?) {
        error("This method is not supported")
    }

    override fun startService(service: Intent?): ComponentName? {
        error("This method is not supported")
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        error("This method is not supported")
    }

    override fun stopService(service: Intent?): Boolean {
        error("This method is not supported")
    }

    override fun bindService(service: Intent?, conn: ServiceConnection, flags: Int): Boolean {
        error("This method is not supported")
    }

    override fun unbindService(conn: ServiceConnection) {
        error("This method is not supported")
    }

    override fun startInstrumentation(
        className: ComponentName,
        profileFile: String?,
        arguments: Bundle?
    ): Boolean {
        error("This method is not supported")
    }

    override fun getSystemService(name: String): Any {
        error("This method is not supported")
    }

    override fun getSystemServiceName(serviceClass: Class<*>): String? {
        error("This method is not supported")
    }

    override fun checkPermission(permission: String, pid: Int, uid: Int): Int {
        error("This method is not supported")
    }

    override fun checkCallingPermission(permission: String): Int {
        error("This method is not supported")
    }

    override fun checkCallingOrSelfPermission(permission: String): Int {
        error("This method is not supported")
    }

    override fun checkSelfPermission(permission: String): Int {
        error("This method is not supported")
    }

    override fun enforcePermission(permission: String, pid: Int, uid: Int, message: String?) {
        error("This method is not supported")
    }

    override fun enforceCallingPermission(permission: String, message: String?) {
        error("This method is not supported")
    }

    override fun enforceCallingOrSelfPermission(permission: String, message: String?) {
        error("This method is not supported")
    }

    override fun grantUriPermission(toPackage: String?, uri: Uri?, modeFlags: Int) {
        error("This method is not supported")
    }

    override fun revokeUriPermission(uri: Uri?, modeFlags: Int) {
        error("This method is not supported")
    }

    override fun revokeUriPermission(toPackage: String?, uri: Uri?, modeFlags: Int) {
        error("This method is not supported")
    }

    override fun checkUriPermission(uri: Uri?, pid: Int, uid: Int, modeFlags: Int): Int {
        error("This method is not supported")
    }

    override fun checkUriPermission(
        uri: Uri?,
        readPermission: String?,
        writePermission: String?,
        pid: Int,
        uid: Int,
        modeFlags: Int
    ): Int {
        error("This method is not supported")
    }

    override fun checkCallingUriPermission(uri: Uri?, modeFlags: Int): Int {
        error("This method is not supported")
    }

    override fun checkCallingOrSelfUriPermission(uri: Uri?, modeFlags: Int): Int {
        error("This method is not supported")
    }

    override fun enforceUriPermission(
        uri: Uri?,
        pid: Int,
        uid: Int,
        modeFlags: Int,
        message: String?
    ) {
        error("This method is not supported")
    }

    override fun enforceUriPermission(
        uri: Uri?,
        readPermission: String?,
        writePermission: String?,
        pid: Int,
        uid: Int,
        modeFlags: Int,
        message: String?
    ) {
        error("This method is not supported")
    }

    override fun enforceCallingUriPermission(uri: Uri?, modeFlags: Int, message: String?) {
        error("This method is not supported")
    }

    override fun enforceCallingOrSelfUriPermission(uri: Uri?, modeFlags: Int, message: String?) {
        error("This method is not supported")
    }

    override fun createPackageContext(packageName: String?, flags: Int): Context {
        error("This method is not supported")
    }

    override fun createContextForSplit(splitName: String?): Context {
        error("This method is not supported")
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        error("This method is not supported")
    }

    override fun createDisplayContext(display: Display): Context {
        error("This method is not supported")
    }

    override fun createDeviceProtectedStorageContext(): Context {
        error("This method is not supported")
    }

    override fun isDeviceProtectedStorage(): Boolean {
        error("This method is not supported")
    }
}