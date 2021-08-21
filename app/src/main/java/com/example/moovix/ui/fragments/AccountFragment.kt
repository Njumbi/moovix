package com.example.moovix.ui.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovix.R
import com.example.moovix.data.model.MovieModel
import com.example.moovix.data.model.SeriesResult
import com.example.moovix.ui.adapters.MovieAdapter
import com.example.moovix.ui.adapters.PopularSeries
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_movie_player.*
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment() {
    private val TAG = "SignUpActivity"
    var mAuth: FirebaseAuth? = null
    private var fDatastore: FirebaseFirestore? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    private var mAuthListener: AuthStateListener? = null
    private val  RC_SIGN_IN = 100
    private var mGoogleApiClient: GoogleApiClient? = null

    private lateinit var adapter: MovieAdapter
    private lateinit var seriesAdapter:PopularSeries

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signIn()
        signOut()
        pb_one.visibility = View.VISIBLE
        ll_welcome.visibility = View.GONE
        frag_account.visibility =View.GONE



        adapter = MovieAdapter()
        rv_favorite_movies.layoutManager =LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rv_favorite_movies.adapter = adapter

        seriesAdapter = PopularSeries()
        rv_favorite_series.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        rv_favorite_series.adapter = seriesAdapter


        // Configure Google Sign In
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        mAuth = FirebaseAuth.getInstance()
        fDatastore = FirebaseFirestore.getInstance()

        register.setOnClickListener {

            if (TextUtils.isEmpty(et_email.text)) {
                tl_email.error = "Enter Email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_password.text)) {
                tl_password.error = "Enter password"
                return@setOnClickListener
            }

            activity?.let { it1 ->
                mAuth!!.createUserWithEmailAndPassword(
                    et_email.text.toString(),
                    et_password.text.toString()
                )
                    .addOnCompleteListener(
                        it1
                    ) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = mAuth!!.currentUser

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                requireActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
            }


        }
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {

                pb_one.visibility = View.GONE
                frag_account.visibility= View.GONE
                ll_welcome.visibility = View.VISIBLE
//                tl_welcome.text = "Welcome: " + user?.displayName
//                tl_welcome_email.text="Email: " + user?.email

                getMovies()
                getSeries()

            } else {
                pb_one.visibility = View.GONE
                frag_account.visibility = View.VISIBLE
                ll_welcome.visibility= View.GONE
            }
         //   updateUI(user)
        }



    }

    private fun getSeries() {
       var list = arrayListOf<SeriesResult>()
       list .clear()
        fDatastore!!.collection("favoriteSeries")
            .whereEqualTo("user",mAuth!!.currentUser?.uid)
            .addSnapshotListener { snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                list .clear()

                for (doc in snapshot!!) {
                    val o = doc.toObject(SeriesResult::class.java)
                    list .add(o)
                }

                seriesAdapter.setData(list)
            }

    }


    private fun getMovies() {
        var list = arrayListOf<MovieModel>()
        list.clear()
        fDatastore!!.collection("favoriteMovies")
            .whereEqualTo("user", mAuth!!.currentUser?.uid)
            .addSnapshotListener { snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                list.clear()

                for (doc in snapshot!!) {
                    val o = doc.toObject(MovieModel::class.java)
                    list.add(o)
                }

                adapter.setData(list)
            }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result: GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result?.isSuccess == true) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result?.signInAccount
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } else {
                // Google Sign In failed, update UI appropriately
               // updateUI(null)
            }
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        activity?.let {
            mAuth!!.signInWithCredential(credential).addOnCompleteListener(
                it,
                OnCompleteListener<AuthResult?> { task ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)
                    if (!task.isSuccessful) {

                    } else {

                    }

                })
        }
    }
    private fun signIn() {
        sign_in_button.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }
   private fun signOut(){
       logout.setOnClickListener {
           mAuth!!.signOut()
           frag_account.visibility = View.VISIBLE
           ll_welcome.visibility= View.GONE
           pb_one.visibility = View.GONE

       }
   }

}














