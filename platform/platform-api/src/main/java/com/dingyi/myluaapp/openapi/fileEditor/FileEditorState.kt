/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.openapi.fileEditor

/**
 * This object is used to store/restore editor state between restarts.
 * For example, text editor can store caret position, scroll position,
 * information about folded regions, etc.
 *
 *
 * Undo subsystem expects a sensible implementation of [Object.equals] method of state instances.
 * In particular, `state1` and `state2` in the following situation
 * <pre>`FileEditorState state1 = fileEditor.getState(FileEditorStateLevel.UNDO);
 * ...
 * fileEditor.setState(state1);
 * FileEditorState state2 = fileEditor.getState(FileEditorStateLevel.UNDO);
`</pre> *
 * are expected to be 'equal'.
 *
 * @author Vladimir Kondratyev
 */
fun interface FileEditorState {
    fun canBeMergedWith(otherState: FileEditorState): Boolean

    companion object {
        val INSTANCE =
            FileEditorState { true }
    }
}