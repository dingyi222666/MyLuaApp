package com.dingyi.myluaapp.build.api.internal

import com.dingyi.myluaapp.build.api.BuildTool
import com.dingyi.myluaapp.build.api.internal.plugins.PluginAwareInternal

interface BuildToolInternal: BuildTool,PluginAwareInternal {
}