package com.androidfizz.androidcurdroompersistence;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidfizz.adapter.AdapterPerson;
import com.androidfizz.dao.PersonDao;
import com.androidfizz.db.PersonDb;
import com.androidfizz.model.Address;
import com.androidfizz.model.ModelPerson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PersonDao mPersonDao;
    private AdapterPerson mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        mPersonDao = PersonDb.getInstance(this).getPersonDao();
        RecyclerView mList = findViewById(R.id.mList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(mLayoutManager);
        mList.setHasFixedSize(true);
        mAdapter = new AdapterPerson(new ArrayList<ModelPerson>());
        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new SingleItemDecoration(this));
        mAdapter.setOnCustomClickListner(new AdapterPerson.OnClickListner() {
            @Override
            public void onClick(ModelPerson person, int type) {
                onAction(person, type);
            }
        });


        getAllPersons();
        //PLEASE USE VIEWMODEL INSTEAD OF getAllPersons();
    }

    private void onAction(ModelPerson person, int type) {
        switch (type) {
            case AdapterPerson.TYPE_UPDATE:
                UpdatePerson(person);
                break;

            case AdapterPerson.TYPE_DELETE:
                mPersonDao.delete(person);
                getAllPersons();
                //PLEASE USE VIEWMODEL INSTEAD OF getAllPersons();
                break;
        }

    }

    private void UpdatePerson(final ModelPerson person) {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_add_person, null);
        final Dialog mDialog = Utils.addPersonDialog(this, view);
        mDialog.show();
        view.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        final EditText etName = view.findViewById(R.id.etName);
        final EditText etEmail = view.findViewById(R.id.etEmail);
        final EditText etAge = view.findViewById(R.id.etAge);

        etName.setText(person.getName());
        etEmail.setText(person.getEmail());
        etAge.setText(person.getAge());
        final Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setText(R.string.update);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setEnabled(false);

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                Address add = new Address("indian", "delhi");
                ModelPerson mPerson = new ModelPerson(name, email, age, add);
                mPerson.setPersonID(person.getPersonID());

                boolean isSuccess = mPersonDao.updatePersonRecord(mPerson) > 0;

                if (isSuccess) {
                    Toast.makeText(MainActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
                    getAllPersons();
                    //PLEASE USE VIEWMODEL INSTEAD OF getAllPersons();
                } else {
                    Toast.makeText(MainActivity.this, R.string.operation_failed, Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
                btnAdd.setEnabled(true);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mAddPerson:
                View view = LayoutInflater.from(this).inflate(R.layout.alert_add_person, null);
                final Dialog mDialog = Utils.addPersonDialog(this, view);
                mDialog.show();
                view.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                final EditText etName = view.findViewById(R.id.etName);
                final EditText etEmail = view.findViewById(R.id.etEmail);
                final EditText etAge = view.findViewById(R.id.etAge);

                final Button btnAdd = view.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnAdd.setEnabled(false);

                        String name = etName.getText().toString().trim();
                        String email = etEmail.getText().toString().trim();
                        String age = etAge.getText().toString().trim();
//For asynchronous
                        /*Observable.fromCallable(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                return mPersonDao.addPerson(new ModelPerson(etName.getText().toString().trim()
                                        , etEmail.getText().toString().trim()
                                        , etAge.getText().toString().trim()));
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Object>() {
                                    @Override
                                    public void accept(Object o) throws Exception {

                                    }
                                });*/
                        Address add = new Address("india", "delhi");
                        boolean isSuccess = mPersonDao.addPerson(new ModelPerson(name, email, age, add)) > 0;
                        if (isSuccess) {
                            Toast.makeText(MainActivity.this, R.string.inserted_successfully, Toast.LENGTH_SHORT).show();
                            getAllPersons();
                            //PLEASE USE VIEWMODEL INSTEAD FOR getAllPersons();
                        } else
                            Toast.makeText(MainActivity.this, R.string.operation_failed, Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                        btnAdd.setEnabled(true);
                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllPersons() {
        List<ModelPerson> mTempPersonList = mPersonDao.getAllPerson();
        mAdapter.setListItems(mTempPersonList);
    }

    private class SingleItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        private SingleItemDecoration(Context context) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount() - 1;
            for (int i = 0; i < childCount; i++) {

                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }

        }
    }
}
