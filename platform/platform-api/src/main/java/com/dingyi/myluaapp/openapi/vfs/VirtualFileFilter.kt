package com.dingyi.myluaapp.openapi.vfs


fun interface VirtualFileFilter {
    fun accept(file: VirtualFile): Boolean
    fun and(other: VirtualFileFilter): VirtualFileFilter {
        return VirtualFileFilter { file: VirtualFile ->
            accept(file) && other.accept(
                file
            )
        }
    }

    companion object {
        val ALL = object : VirtualFileFilter {
            override fun accept(file: VirtualFile): Boolean {
                return true
            }

            override fun toString(): String {
                return "ALL"
            }
        }
        val NONE = object : VirtualFileFilter {
            override fun accept(file: VirtualFile): Boolean {
                return false
            }

            override fun toString(): String {
                return "NONE"
            }
        }
    }
}
