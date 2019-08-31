package com.sample.domain.core

class BehaviorSubjectImplementation<T>(
    private var item: T? = null
) {

    private val observers = mutableListOf<(T) -> Unit>()

    fun subscribe(onNext: (T) -> Unit) {
        item?.also { onNext(it) }
        observers.add(onNext)
    }

    fun onNext(item: T) {
        this.item = item
        observers.forEach { it.invoke(item) }
    }

    fun onComplete(){
        observers.clear()
        item = null
    }

}