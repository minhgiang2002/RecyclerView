package com.example.lap42;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHoder > {
    private Context context ;
    private List<Course> coursesList;

    public CourseAdapter(Context context, List<Course> coursesList) {
        this.context = context;
        this.coursesList = coursesList;
    }


    @Override
    public CourseViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseViewHoder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHoder holder, int position) {
        Course course = coursesList.get(position);
        holder.courseCode.setText(course.courseCode);
        holder.courseName.setText(course.courseName);
        holder.lecturerName.setText(course.lecturerName);
        //holder.courseImge .setImageResource(course.courseImage);
        Picasso.get().load(course.getCourseImage()).into(holder.courseImge);
        int imageResource = context.getResources().getIdentifier(course.getCourseImage(), "drawable", context.getPackageName());
        holder.courseImge.setImageResource(imageResource);


    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }
    public static class CourseViewHoder extends RecyclerView.ViewHolder{
        TextView courseCode;
        TextView courseName;
        TextView lecturerName;
        ImageView courseImge;
        public CourseViewHoder(View itemView){
            super(itemView);
            courseCode = itemView.findViewById(R.id.course_code);
            courseName = itemView.findViewById(R.id.course_name);
            lecturerName = itemView.findViewById(R.id.lecturer_name);
            courseImge = itemView.findViewById(R.id.course_image);
        }
    }
}
