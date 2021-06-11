package container.restaurant.android.presentation.base

import androidx.fragment.app.Fragment
import container.restaurant.android.dialog.AlertDialog

abstract class BaseFragment : Fragment()  {

    fun simpleDialog(title: String, msg: String, type: Int) {
        (requireActivity() as BaseActivity).simpleDialog(title, msg, type)
    }

    fun loadingCheck(isLoading:Boolean) {
        if(isLoading) showLoading() else hideLoading()
    }

    fun showLoading() {
        (requireActivity() as BaseActivity).showLoading()
    }
    fun hideLoading() {
        (requireActivity() as BaseActivity).hideLoading()
    }

    fun errorDialog(msg: String) {
        simpleDialog("Warning", msg, AlertDialog.WARNING_TYPE)
    }
}