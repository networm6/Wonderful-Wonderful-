package com.mycompany.myvideo.adap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mycompany.myvideo.Bean;
import com.mycompany.myvideo.R;
import com.mycompany.myvideo.VideoPlayer;
import com.mycompany.myvideo.adap.StudyRvAdapter;
import com.mycompany.myvideo.util;
import java.util.List;

public class StudyRvAdapter extends RecyclerView.Adapter<StudyRvAdapter.ViewHolder>
 {
    private List<Bean> mList;
    private Context mContext;
	//构造器 获取要显示的数据和Context
    public StudyRvAdapter(List<Bean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }
	//初始化方法,设置布局样式，返回布局
	//对控件的赋值不可以写在这里，写在这里只会显示一条。只执行一次
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
			.inflate(R.layout.item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
	//数据绑定 绑定控件对应事件
	//对控件赋值(如：textview赋值)的操作在这个方法里写
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tv_info1.setText(""+mList.get(i).getTitle());
        viewHolder.tv_info1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					util.id= mList.get(i).getId();
					mContext.startActivity(new Intent(mContext,VideoPlayer.class));
					
				}
			});
    }
	//返回item数量
	//这个方法默认创建return 0 不修改的话recyclerview会不显示数据
    @Override
    public int getItemCount() {
        return mList.size();
    }
	//获取item索引
    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }
	//Item中的控件
    static class ViewHolder extends RecyclerView.ViewHolder{
        
        TextView tv_info1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         tv_info1 = itemView.findViewById(R.id.itemTextView20);
        }
    }
}
