package com.dingyi.myluaapp.build.api.internal.tasks

enum class TaskExecutionOutcome(
    val isSkipped: Boolean,
    val isUpToDate: Boolean,
    val message: String?
) {
    FROM_CACHE(true, true, "FROM-CACHE"), UP_TO_DATE(true, true, "UP-TO-DATE"), SKIPPED(
        true,
        false,
        "SKIPPED"
    ),
    NO_SOURCE(true, false, "NO-SOURCE"), EXECUTED(false, false, null);

    companion object {
        fun valueOf(outcome: ExecutionOutcome): TaskExecutionOutcome {
            return when (outcome) {
                ExecutionOutcome.FROM_CACHE -> FROM_CACHE
                ExecutionOutcome.UP_TO_DATE -> UP_TO_DATE
                ExecutionOutcome.SHORT_CIRCUITED -> NO_SOURCE
                ExecutionOutcome.EXECUTED_INCREMENTALLY, ExecutionOutcome.EXECUTED_NON_INCREMENTALLY -> EXECUTED
            }
        }
    }
}
