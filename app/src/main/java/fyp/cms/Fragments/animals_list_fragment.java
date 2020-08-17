package fyp.cms.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fyp.cms.Firebase_Operations.firebase_operations;
import fyp.cms.R;

public class animals_list_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list,container,false);
        RecyclerView list=v.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebase_operations.getAnimalsForAdmin(getActivity(),list,this);
        return v;
    }
}
