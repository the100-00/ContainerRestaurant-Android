package container.restaurant.android.presentation.feed.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import container.restaurant.android.R

internal class FeedAllHelpDialogFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feed_all_help, container, false)
        view.findViewById<TextView>(R.id.tv_confirm).setOnClickListener {
            dismissAllowingStateLoss()
        }
        return view
    }
}