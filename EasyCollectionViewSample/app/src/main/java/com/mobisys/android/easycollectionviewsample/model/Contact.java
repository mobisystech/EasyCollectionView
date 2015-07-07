package com.mobisys.android.easycollectionviewsample.model;

import com.mobisys.android.easycollectionview.annotations.ViewId;
import com.mobisys.android.easycollectionviewsample.R;

/**
 * Created by priyank on 7/1/15.
 */
public class Contact {

    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @ViewId(id= R.id.name)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ViewId(id= R.id.phone)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ViewId(id= R.id.email)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        if (phone != null ? !phone.equals(contact.phone) : contact.phone != null) return false;
        return !(email != null ? !email.equals(contact.email) : contact.email != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
