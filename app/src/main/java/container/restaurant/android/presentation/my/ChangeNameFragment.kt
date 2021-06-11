package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import container.restaurant.android.data.response.UpdateProfileResponse
import container.restaurant.android.databinding.FragmentChangeNameBinding
import container.restaurant.android.dialog.AlertDialog
import container.restaurant.android.presentation.base.BaseFragment
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeNameFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeNameBinding

    private val viewModel: MyViewModel by viewModel()

    private val args: ChangeNameFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setBindItem()
        subscribeUi()

        return binding.root
    }

    private fun setBindItem() {
        binding.apply {
            etNickName.setText(args.nickName)

            btnComplete.setOnClickListener {
                val newNickName = etNickName.text.toString()
                observe(viewModel.updateProfile(args.userId, newNickName, args.profile), ::completeProfile)
            }

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
            observe(viewLoading, ::loadingCheck)
            observe(getErrorMsg, ::errorDialog)
        }
    }

    private fun completeProfile(profileResponse: UpdateProfileResponse) {
        simpleDialog("Success", "닉네임이 변경되었습니다.", AlertDialog.SUCCESS_TYPE)
    }
}