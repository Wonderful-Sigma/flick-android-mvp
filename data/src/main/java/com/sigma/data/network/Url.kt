package com.sigma.data.network

import com.sigma.data.network.dto.qrcode.JWTGenerateResponse
import com.sigma.data.network.dto.qrcode.JwtDecodingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

object FlickUrl {
    object Wallet {
        const val remit = "/api/wallet/management/remit"
        const val get = "/api/wallet/{walletId}"
        const val searchToNumber = "/api/wallet/search/accountNumber/{accountNumber}"
        const val searchToId = "/api/wallet/search/{memberId}"
    }
    object QrCode {
        const val generate = "/api/QrCode/approval/{walletId}"
        const val decoding = "/api/QrCode/search/qr"
    }
    object Spend {
        const val recent = "/api/spend/recent/remit/{memberId}"
        const val all = "/api/spend/{walletId}"
    }
    object Member {
        const val login = "/api/member/login"
        const val member = "/api/member/member"
        const val newAccessToken = "/api/member/newAccessToken"
        const val firebase = "/api/member/setFirebase/{memberId}"
    }
    object Manager {
        const val event = "/api/manager/event"
    }
    object Notification {
        const val alarm = "/api/noti/{walletId}"
    }
}