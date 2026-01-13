package com.example.umvisamate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    // 增加点击接口
    public interface OnItemClickListener {
        void onItemClick(Student student);
    }

    private OnItemClickListener listener;
    private List<Student> studentList;

    public ApplicationAdapter(List<Student> list) {
        this.studentList = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_application, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);

        // 1. 修正变量名对齐
        holder.tvStudentName.setText(student.getName());

        // 2. 修正：调用你 Student 类里的 getPassportId() 方法
        holder.tvStudentId.setText("ID: " + student.getPassportId());

        // 3. 动态设置头像首字母
        if (student.getName() != null && !student.getName().isEmpty()) {
            String initial = student.getName().substring(0, 1).toUpperCase();
            holder.tvStudentInitial.setText(initial);
        }

        // 4. 设置状态文本
        holder.tvStatus.setText(student.getStatus());

        // 5. 设置点击监听，确保点击行能跳转
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(student);
            }
        });
    }

    @Override
    public int getItemCount() { return studentList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 在这里统一定义所有变量名，防止找不到符号
        TextView tvStudentName, tvStudentId, tvStatus, tvStudentInitial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 与 item_application.xml 里的 ID 对应
            tvStudentName = itemView.findViewById(R.id.studentName);
            tvStudentId = itemView.findViewById(R.id.studentId);
            tvStatus = itemView.findViewById(R.id.statusText);
            // 确保你在 XML 中为头像 TextView 设置了 android:id="@+id/student_initial"
            tvStudentInitial = itemView.findViewById(R.id.student_initial);
        }
    }
}