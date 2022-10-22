package com.dingyi.myluaapp.ide.completion

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.language.LanguageExtensionPoint


class CompletionContributorEP(
    language: String,
    implementationClass: String,
    pluginDescriptor: PluginDescriptor
) : LanguageExtensionPoint<CompletionContributor>(
    language,
    implementationClass, pluginDescriptor
) {


}
