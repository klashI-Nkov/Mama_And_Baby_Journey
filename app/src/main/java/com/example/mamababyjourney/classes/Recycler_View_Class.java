package com.example.mamababyjourney.classes;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings ( "unused" )
public class Recycler_View_Class
{

    public static List < Recycler_View_Class > recycler_View_Class_Object_List = new ArrayList < > ( ) ;

    String content , title;

    public Recycler_View_Class ( String title , String content )
    {
        this . content = content ;
        this . title   = title   ;
    }

    public String getContent ( ) { return content; }

    public String getTitle ( ) { return title; }
}
