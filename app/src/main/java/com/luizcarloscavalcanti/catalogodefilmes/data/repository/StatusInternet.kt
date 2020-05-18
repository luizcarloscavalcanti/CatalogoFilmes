package com.luizcarloscavalcanti.catalogodefilmes.data.repository

import com.luizcarloscavalcanti.catalogodefilmes.R

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class StatusInternet (val status: Status, val msg: String) {
    companion object {
        val LOADED: StatusInternet
        val LOADING: StatusInternet
        val ERROR: StatusInternet
        val ENDOFLIST: StatusInternet

        init {
            LOADED = StatusInternet(Status.SUCCESS, R.string.msg_succes.toString())
            LOADING = StatusInternet(Status.RUNNING, R.string.msg_running.toString())
            ERROR = StatusInternet(Status.FAILED, R.string.msg_failed.toString())
            ENDOFLIST = StatusInternet(Status.FAILED, R.string.msg_end_list.toString())
        }
    }
}