package com.sigma.data.mapper

import com.sigma.data.network.dto.account.AccountResponseDto
import com.sigma.data.network.dto.account.CheckAlarmDto
import com.sigma.data.network.dto.account.FixMemberRequestDto
import com.sigma.data.network.dto.account.EventRequestDto
import com.sigma.data.network.dto.account.JwtResponseDto
import com.sigma.data.network.dto.account.MakeZeroRequestDto
import com.sigma.data.network.dto.account.SpendResponseDto
import com.sigma.data.network.dto.account.RemitRequestDto
import com.sigma.data.network.dto.account.MemberResponseDto
import com.sigma.data.network.dto.account.MessageBodyRequestDto
import com.sigma.data.network.dto.account.SpendCalculateRequestDto
import com.sigma.data.network.dto.account.StatusResponseDto
import com.sigma.data.network.dto.account.WalletResponseDto
import com.sigma.main.model.account.Account
import com.sigma.main.model.account.AccountResponseModel
import com.sigma.main.model.account.CheckAlarmModel
import com.sigma.main.model.account.EventRequestModel
import com.sigma.main.model.account.FixMemberRequestModel
import com.sigma.main.model.account.JwtResponseModel
import com.sigma.main.model.account.MakeZeroRequestModel
import com.sigma.main.model.account.SpendResponseModel
import com.sigma.main.model.account.RemitRequestModel
import com.sigma.main.model.account.MemberResponseModel
import com.sigma.main.model.account.MessageBodyRequestModel
import com.sigma.main.model.account.SpendCalculateRequestModel
import com.sigma.main.model.account.StatusResponseModel
import com.sigma.main.model.account.WalletResponseModel

fun RemitRequestModel.toDto() = RemitRequestDto(
    remittanceAccount = this.remittanceAccount,
    money = this.money,
    depositAccount = this.depositAccount
)

fun StatusResponseDto.toModel() = StatusResponseModel(
    status = this.status,
    message = this.message
)

fun FixMemberRequestModel.toDto() = FixMemberRequestDto(
    targetMember = this.targetMember,
    triggerMember = this.triggerMember
)
fun SpendCalculateRequestModel.toDto() = SpendCalculateRequestDto(
    searchDate = this.searchDate,
    accountId = this.accountId
)

fun EventRequestModel.toDto() = EventRequestDto(
    targetMember = this.targetMember,
    money = this.money
)

fun MessageBodyRequestModel.toDto() = MessageBodyRequestDto(
    title = this.title,
    body = this.body
)

fun CheckAlarmDto.toModel() = CheckAlarmModel(
    body = this.body,
    createdDate = this.createdDate,
    id = this.id,
    memberId = this.memberId,
    title = this.title
)

fun AccountResponseDto.toModel() = AccountResponseModel(
    accountType = this.accountType,
    id = this.id,
    managerId = this.managerId,
    money = this.money,
    name = this.name,
    number = this.number,
    spendLists = this.spendLists
)

fun JwtResponseDto.toModel() = JwtResponseModel(
    jwt = this.jwt
)

fun MakeZeroRequestModel.toDto() = MakeZeroRequestDto(
    makeZeroType = this.makeZeroType,
    walletId = this.walletId
)

fun MemberResponseDto.toModel(): MemberResponseModel {
    return MemberResponseModel(
        id = this.id,
        firebaseToken = this.firebaseToken,
        name = this.name,
        memberRule = memberRule,
        studentNumber = this.studentNumber,
        accounts = this.accounts.map{
            Account(
                id = it.id,
                number = it.number,
                managerId = it.managerId,
                accountType = it.accountType,
                fileUrl = it.fileUrl,
                money = it.money,
                name = it.name
            )
        }
    )
}

fun SpendResponseDto.toModel(): SpendResponseModel {
    return SpendResponseModel(
        createdDate = this.createdDate,
        spendType = this.spendType,
        targetMember = this.targetMember,
        balance = this.balance,
        targetAccount = this.targetAccount,
        money = this.money
    )
}

fun WalletResponseDto.toModel(): WalletResponseModel {
    return WalletResponseModel(
        id = this.id,
        number = this.number,
        name = this.name,
        money = this.money,
        managerId = this.managerId,
        accountType = this.accountType,
        fileUrl = this.fileUrl,
        memberList = this.memberList
    )
}