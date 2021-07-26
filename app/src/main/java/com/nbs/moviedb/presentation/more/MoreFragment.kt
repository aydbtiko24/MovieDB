package com.nbs.moviedb.presentation.more

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nbs.moviedb.databinding.FragmentMoreBinding
import com.nbs.moviedb.presentation.utils.FragmentViewDataBinding

/**
 * Created by aydbtiko on 7/26/2021.
 *
 */
class MoreFragment : FragmentViewDataBinding<FragmentMoreBinding>() {

    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoreBinding {
        return FragmentMoreBinding.inflate(inflater, container, false)
    }

    override fun initView() {}

    override fun observeData() {}

    override fun cleanUpAdapter() {}
}