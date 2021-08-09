package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import container.restaurant.android.databinding.FragmentMySettingBinding
import container.restaurant.android.dialog.SimpleConfirmDialog
import container.restaurant.android.presentation.auth.KakaoSignInDialogFragment
import container.restaurant.android.presentation.user.UserProfileActivity
import container.restaurant.android.util.EventObserver
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MySettingFragment : Fragment() {

    private lateinit var binding: FragmentMySettingBinding

    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMySettingBinding.inflate(inflater, container, false)
            .apply{
                this.viewModel =  this@MySettingFragment.viewModel
                this.lifecycleOwner = this@MySettingFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun showSignOutConfirmDialog(){
        val dialog = SimpleConfirmDialog(
            titleStr = "정말 로그아웃 하시겠어요?",
            rightBtnStr = "취소",
            leftBtnStr = "로그아웃"
        )
        dialog.setMultiEventListener(object : SimpleConfirmDialog.MultiEventListener {
            override fun onRightBtnClick(dialogSelf: SimpleConfirmDialog) {
                dialogSelf.dismiss()
            }

            override fun onLeftBtnClick(dialogSelf: SimpleConfirmDialog) {
                //Todo 로그아웃 동작
                dialogSelf.dismiss()
            }
        })
        dialog.show(childFragmentManager,"SimpleConfirmDialog")
    }

    private fun showWithdrawalConfirmDialog(){
        val dialog = SimpleConfirmDialog(
            titleStr = "계정 탈퇴 시 다시 되돌릴 수 없습니다.\n정말 탈퇴하시겠어요?",
            rightBtnStr = "취소",
            leftBtnStr = "계정 탈퇴"
        )
        dialog.setMultiEventListener(object : SimpleConfirmDialog.MultiEventListener {
            override fun onRightBtnClick(dialogSelf: SimpleConfirmDialog) {
                dialogSelf.dismiss()
            }

            override fun onLeftBtnClick(dialogSelf: SimpleConfirmDialog) {
                //Todo 계정 탈퇴 동작
                dialogSelf.dismiss()
            }
        })
        dialog.show(childFragmentManager,"SimpleConfirmDialog")
    }

    private fun observeData() {
        with(viewModel){
            observe(isBackButtonClicked) {
                val directions = MySettingFragmentDirections.actionMySettingFragmentToMyHomeFragment()
                findNavController().navigate(directions)
            }
            isPrivacyPolicyButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if(it) {
                    val directions = MySettingFragmentDirections.actionMySettingFragmentToPrivacyPolicyFragment()
                    findNavController().navigate(directions)
                }
            })
            isTermsOfPolicyButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if(it) {
                    val directions = MySettingFragmentDirections.actionMySettingFragmentToTermsOfServiceFragment()
                    findNavController().navigate(directions)
                }
            })
            isSignOutButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if(it) {
                    showSignOutConfirmDialog()
                }
            })

            isWithdrawalButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if(it) {
                    showWithdrawalConfirmDialog()
                }
            })
        }
    }
}