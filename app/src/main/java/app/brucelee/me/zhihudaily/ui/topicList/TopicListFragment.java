package app.brucelee.me.zhihudaily.ui.topicList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import app.brucelee.me.zhihudaily.R;
import app.brucelee.me.zhihudaily.ZhihuApplication;
import app.brucelee.me.zhihudaily.bean.Topic;
import app.brucelee.me.zhihudaily.bean.TopicList;
import app.brucelee.me.zhihudaily.service.ZhihuService;
import app.brucelee.me.zhihudaily.ui.BaseActivity;
import app.brucelee.me.zhihudaily.ui.BaseFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by bruce on 6/26/14.
 */
public class TopicListFragment extends BaseFragment implements AbsListView.OnScrollListener, TopicListView {
    @InjectView(R.id.clv_topic_card_list) CardListView cardListView;
    @Inject TopicListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_list, container, false);
        ButterKnife.inject(this, view);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCards();
    }


    private void initCards() {
        cardListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true, this));
        presenter.fetch();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }

    @Override
    public List<Object> getModules() {
        return Arrays.<Object>asList(new TopicListModule(this));
    }

    @Override
    public CardListView getListView() {
        return cardListView;
    }

    @Override
    public void setItems(TopicList topicList) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (Topic topic : topicList.others) {
            CardExample card = new CardExample(getActivity(), topic);
            cards.add(card);
        }
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
        cardListView.setAdapter(cardArrayAdapter);
    }

    public class CardExample extends Card{
        private Topic topic;
        private Context context;
        public CardExample(Context context,Topic topic) {
            super(context, R.layout.topic_card);
            this.context = context;
            this.topic = topic;
            init();
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            TextView title = (TextView) parent.findViewById(R.id.tv_topic_card_title);
            TextView description = (TextView) parent.findViewById(R.id.tv_topic_card_description);
            ImageView image = (ImageView) parent.findViewById(R.id.iv_topic_card_image);
            title.setText(topic.name);
            description.setText(topic.description);
            ImageLoader.getInstance().displayImage(topic.image, image);
        }

        private void init(){
            //Add ClickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
