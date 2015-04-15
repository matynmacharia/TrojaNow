package name.yyx.trojanow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.andraskindler.quickscroll.QuickScroll;
import com.andraskindler.quickscroll.Scrollable;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendFragment extends Fragment {

    private FriendAdapter adapter;
    private List<Map<String, Object>> data;
    private QuickScroll scroll;
    private EditText search;
    private ListView list;

    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        return fragment;
    }

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data", (Serializable) data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            data = (List<Map<String, Object>>) savedInstanceState.getSerializable("data");
        } else {
            // list content
            data = new ArrayList<Map<String, Object>>();
            String[] author = {"an","an","an","an","an","yao", "yao","yao","yao","yao","zhang","zhang","zhang","zhang","zhang"};
            for (int i = 0; i < 15; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user", author[i]);
                map.put("nickname", "ABCDEFG");
                data.add(map);
            }
        }

        adapter = new FriendAdapter(
                getActivity(), data, R.layout.listview_item_friend,
                new String[]{"user", "nickname"},
                new int[]{R.id.friend_username, R.id.friend_nickname}
        );

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_friend, container, false);
        list = (ListView)rootView.findViewById(R.id.list_friend);
        scroll = (QuickScroll)rootView.findViewById(R.id.quickscroll);
        search = (EditText)rootView.findViewById(R.id.et_seatch);
        list.setAdapter(adapter);

        scroll.init(QuickScroll.TYPE_INDICATOR_WITH_HANDLE, list, adapter, QuickScroll.STYLE_HOLO);
        scroll.setFixedSize(1);
        scroll.setHandlebarColor(QuickScroll.GREY_DARK, QuickScroll.GREY_LIGHT, QuickScroll.GREY_SCROLLBAR);
        scroll.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 48);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    public class FriendAdapter extends SimpleAdapter implements Scrollable {

        public FriendAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public String getIndicatorForPosition(int childposition, int groupposition) {
            String str = data.get(childposition).get("user").toString();
            return Character.toString(str.charAt(0));
        }

        @Override
        public int getScrollPosition(int childposition, int groupposition) {
            return childposition;
        }
    }

}