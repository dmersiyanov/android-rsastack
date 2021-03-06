package com.myapp.ui.splash

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_splash.*
import com.myapp.ui.common.BaseFragment
import com.myapp.R
import com.myapp.toothpick.DI
import com.rsastack.system.utils.visible
import toothpick.Toothpick

class SplashFragment : BaseFragment() , SplashView,
    com.rsastack.system.navigation.BackButtonListener
{
    override val layoutRes = R.layout.fragment_splash
    private var errorSnackbar: Snackbar? = null

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        SplashPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showProgress() {
        progress.visible(true)
        errorSnackbar?.dismiss()
    }

    override fun showError(msg: String) {
        progress.visible(false)
        errorSnackbar?.dismiss()

        view?.let {view ->
            errorSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Retry") { presenter.onRetry() }
                show()
            }
        }
    }

    override fun onBackPressed() {
        presenter.onBack()
    }
}