package com.example.deeknut.buzzmovie;

import android.provider.ContactsContract;

/**
 * Class for profile queries
 */
public final class ProfileQuery {
    /**
     * Private constructor. Eliminates instatiation outside of this utility class.
     */
    private ProfileQuery() {

    }

    /**
     * String array for Projection
     */
    private static final String[] PROJECTION = {
        ContactsContract.CommonDataKinds.Email.ADDRESS,
        ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
    };

    /**
     * Final int variable for the address
     */
    private static final int ADDRESS = 0;

    /**
     * Gets address of ProfileQuery.
     * @return address of profile query.
     */
    public static int getAddress() {
        return ADDRESS;
    }

    /**
     * Gets Projection of ProfileQuery.
     * @return projection of profile query.
     */
    public static String[] getProjection() {
        return PROJECTION;
    }
}
