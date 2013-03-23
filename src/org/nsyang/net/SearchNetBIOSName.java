package org.nsyang.net;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class SearchNetBIOSName {
	public static void main(String[] args) {
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://192.168.0.101");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "cn=administrator,cn=users,dc=my,dc=domain,dc=com");
            env.put(Context.SECURITY_CREDENTIALS, "********");
            LdapContext context = new InitialLdapContext(env, null);
            String searchBase = "cn=Partitions,cn=Configuration,dc=my,dc=domain,dc=com";
            String searchFilter = "(&(objectcategory=Crossref)(netbiosname=*))";
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            NamingEnumeration answers = context.search(searchBase, searchFilter, controls);
            while (answers.hasMore()) {
                SearchResult rs = (SearchResult) answers.next();
                String netBiosName = rs.getAttributes().get("NetBIOSName").get(0).toString();
                System.out.println(netBiosName);
            }
            context.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
