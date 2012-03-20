package client;

import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;

import interfaces.Message;
import interfaces.User;

public class UserImpl implements User {
	
	private String login;

	@Override
	public String login() {
		return login;
	}

	@Override
	public void notifyMessage(Message message) {

	}

	@Override
	public void notifyConnection(User user) {

	}

	@Override
	public Request _create_request(Context ctx, String operation,
			NVList arg_list, NamedValue result) {
		return null;
	}

	@Override
	public Request _create_request(Context ctx, String operation,
			NVList arg_list, NamedValue result, ExceptionList exclist,
			ContextList ctxlist) {
		return null;
	}

	@Override
	public Object _duplicate() {
		return null;
	}

	@Override
	public DomainManager[] _get_domain_managers() {
		return null;
	}

	@Override
	public Object _get_interface_def() {
		return null;
	}

	@Override
	public Policy _get_policy(int policy_type) {
		return null;
	}

	@Override
	public int _hash(int maximum) {
		return 0;
	}

	@Override
	public boolean _is_a(String repositoryIdentifier) {
		return false;
	}

	@Override
	public boolean _is_equivalent(Object other) {
		return false;
	}

	@Override
	public boolean _non_existent() {
		return false;
	}

	@Override
	public void _release() {

	}

	@Override
	public Request _request(String operation) {
		return null;
	}

	@Override
	public Object _set_policy_override(Policy[] policies,
			SetOverrideType set_add) {
		return null;
	}

}
