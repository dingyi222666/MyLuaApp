// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.diagnostic

import java.util.logging.Level

enum class LogLevel(val level: Level) {
    OFF(Level.OFF), ERROR(Level.SEVERE), WARNING(Level.WARNING), INFO(Level.INFO), DEBUG(Level.FINE), TRACE(
        Level.FINER
    ),
    ALL(Level.ALL);

    val levelName: String
        get() = level.name
}