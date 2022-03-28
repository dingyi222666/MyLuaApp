package com.dingyi.myluaapp.editor.complete

import androidx.annotation.WorkerThread
import io.github.rosemoe.sora.lang.completion.CompletionPublisher
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import org.checkerframework.checker.guieffect.qual.UI

interface AutoCompleteProvider {

    @WorkerThread
    suspend fun requireAutoComplete(content:ContentReference,position: CharPosition,publisher:CompletionPublisher)


}