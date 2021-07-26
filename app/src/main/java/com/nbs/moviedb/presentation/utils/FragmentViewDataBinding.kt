package com.nbs.moviedb.presentation.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


/**
 * an abstract class for inflate or destroying ViewDataBinding
 */
abstract class FragmentViewDataBinding<V : ViewDataBinding> : Fragment() {
    /**
     * As [Android Developer Guide](https://developer.android.com/topic/libraries/view-binding#fragments)
     *
     * Fragments outlive their views.
     * Make sure you clean up any references to the binding class instance in the fragment's
     * onDestroyView() method.
     */
    private var _binding: V? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding: V get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = createViewDataBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    protected abstract fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?): V
    protected abstract fun initView()
    protected abstract fun observeData()
    protected abstract fun cleanUpAdapter()

    override fun onDestroyView() {
        cleanUpAdapter()
        super.onDestroyView()
        _binding = null
    }

}
