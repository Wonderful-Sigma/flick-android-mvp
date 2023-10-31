package com.sigma.flick.feature.event.screen

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentEventBinding
import com.sigma.flick.feature.event.decoration.EventDecoration
import com.sigma.flick.feature.event.recyclerview.EventAdapter
import com.sigma.flick.feature.event.recyclerview.EventData
import com.sigma.flick.feature.event.viewmodel.EventViewModel

class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>(R.layout.fragment_event) {
    override val viewModel: EventViewModel by viewModels()
    override fun start() {
        var data = listOf(
            EventData(R.drawable.circle, "교복 입고", "코인 받기"),
            EventData(R.drawable.circle, "만보기", "130코인 받기"),
            EventData(R.drawable.circle, "오늘의 행운 복권", "코인 받기"),
            EventData(R.drawable.circle, "행운 퀴즈", "코인 받기"),
        )
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.eventRecyclerView.addItemDecoration(EventDecoration())
        binding.eventRecyclerView.adapter = EventAdapter(data)
    }
}