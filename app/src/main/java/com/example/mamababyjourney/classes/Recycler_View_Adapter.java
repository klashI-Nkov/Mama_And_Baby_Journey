package com.example.mamababyjourney.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamababyjourney.R;

import java.util.List;

public class Recycler_View_Adapter extends RecyclerView. Adapter < Recycler_View_Adapter. View_Holder >
{
    List < Recycler_View_Class > recycler_View_Class_Object_List ;

    Context context ;

    public Recycler_View_Adapter ( List < Recycler_View_Class > recycler_View_Class_Object_List , Context context )
    {
        this . recycler_View_Class_Object_List = recycler_View_Class_Object_List ;
        this . context = context ;
    }

    @NonNull
    @Override
    public Recycler_View_Adapter. View_Holder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType )
    {
        LayoutInflater inflater = LayoutInflater . from ( context ) ;
        View view = inflater . inflate ( R. layout . layouts_mother_section_recycler_view_row_layout ,parent ,false ) ;
        return new Recycler_View_Adapter. View_Holder ( view ) ;
    }

    public void ClearData ( )
    {
        recycler_View_Class_Object_List . clear ( ) ;
    }

    @Override
    public void onBindViewHolder ( @NonNull Recycler_View_Adapter.View_Holder holder , int index )
    {
        holder . sub_Title_Tv . setText ( recycler_View_Class_Object_List . get ( index ) . title   ) ;
        holder . content_Tv   . setText ( recycler_View_Class_Object_List . get ( index ) . content ) ;
    }

    @Override
    public int getItemCount ( ) { return recycler_View_Class_Object_List . size ( ) ; }

    public static class View_Holder extends RecyclerView . ViewHolder
    {
        TextView sub_Title_Tv , content_Tv ;

        public View_Holder ( @NonNull View view )
        {
            super ( view ) ;
            sub_Title_Tv = view . findViewById ( R . id . Sub_Title ) ;
            content_Tv   = view . findViewById ( R . id . Content   ) ;
        }
    }

}