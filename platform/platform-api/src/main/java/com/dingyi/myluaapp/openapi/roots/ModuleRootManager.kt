package com.dingyi.myluaapp.openapi.roots

import com.dingyi.myluaapp.openapi.module.Module

abstract class RootManager {

    abstract fun getFileIndex():FileIndex

    /**
     * Returns the list of modules on which the current module directly depends. The method does not traverse
     * the entire dependency structure - dependencies of dependency modules are not included in the returned list.
     *
     * @return the array of module direct dependencies.
     */
    abstract fun getDependencies(): Array<Module>

}