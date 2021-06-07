package com.example.ndsmovieapp.Data.Repository


enum class Status{
    RUNNING,
    SUCCESS,
    FAILED,
    LASTPAGE
}

class NetworkState (val status:Status , val msg:String){

    companion object{
        val LOADED :NetworkState
        val LOADING :NetworkState
        val ERROR : NetworkState
        val LAST : NetworkState
            init {
                LOADED = NetworkState(Status.SUCCESS,"Berhasil")
                LOADING = NetworkState(Status.RUNNING,"Loading..")
                ERROR = NetworkState(Status.FAILED,"Terjadi Kesalahan")
                LAST = NetworkState(Status.FAILED,"Halaman Terakhir")
            }
    }


}