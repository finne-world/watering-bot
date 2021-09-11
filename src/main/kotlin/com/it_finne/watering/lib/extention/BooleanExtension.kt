package com.it_finne.watering.lib.extention

inline fun Boolean.runIfTrue(block: () -> Unit) {
    this.runIf(true) { block() }
}

inline fun Boolean.runIf(bool: Boolean, block: () -> Unit) {
    if (this == bool) {
        block()
    }
}
