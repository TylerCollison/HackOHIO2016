package com.ohio.hack.hackohio2016;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler Collison on 11/19/2016.
 */
public class SkillsViewAdapter extends RecyclerView.Adapter<SkillsViewAdapter.ViewHolder> {

    Context owningContext;
    List<Skill> skills = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView skillText;
        TextView usernameText;

        public ViewHolder(View itemView) {
            super(itemView);
            skillText = (TextView)itemView.findViewById(R.id.skillTextInput);
            usernameText = (TextView)itemView.findViewById(R.id.usernameText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent skillViewIntent = new Intent(owningContext, SkillView.class);
                    Skill selectedSkill = skills.get(getAdapterPosition());
                    String params = selectedSkill.getSkill().toString() +
                            "~~" + selectedSkill.getUsername().toString() + "~~" + selectedSkill.getEmail().toString() +
                            "~~" + selectedSkill.getDescription().toString() + "~~";
                    for (String s : selectedSkill.getInterests()) {
                        params = params + s + "``";
                    }
                    skillViewIntent.putExtra("selectedSkill", params);
                    owningContext.startActivity(skillViewIntent);
                }
            });
        }
    }

    @Override
    public SkillsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // set the skill card as the layout for recycler view items
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SkillsViewAdapter.ViewHolder holder, int position) {
        // Bind the skill data to the ViewHolder
        holder.skillText.setText(skills.get(position).getSkill().toUpperCase());
        holder.usernameText.setText(skills.get(position).getUsername());
    }

    public void addSkill (Skill skill) {
        skills.add(skill);
        System.out.println("skill size = " + skills.size());
    }

    public void setContext (Context newContext) {
        owningContext = newContext;
    }

    public void clear () {
        skills.clear();
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
}
