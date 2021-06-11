package container.restaurant.android.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import container.restaurant.android.R
import container.restaurant.android.databinding.DialogProgressBinding

class ProgressDialog(context: Context) : AlertDialog(context) {

    private lateinit var binding: DialogProgressBinding

    private val modelInAnim: Animation get() = AnimationUtils.loadAnimation(context, R.anim.modal_in)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_progress, null, false)
        setContentView(binding.root)

        Glide.with(context).load(R.drawable.img_level_up).into(binding.loading)
    }

    override fun onStart() {
        super.onStart()
        binding.root.startAnimation(modelInAnim)
    }

    override fun show() {
        super.show()
        showCheck = true
    }

    override fun dismiss() {
        super.dismiss()
        showCheck = false
    }

    companion object {
        var showCheck = false
    }
}