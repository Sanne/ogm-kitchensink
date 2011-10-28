package org.jboss.as.quickstarts.kitchensink.interceptor;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;

@Transactional
@Interceptor
public class RequiredTransactionInterceptor implements Serializable {
	@Resource
	private UserTransaction utx;

	@Inject
	private Logger log;

	@AroundInvoke
	public Object openIfNoTransaction(InvocationContext ic) throws Throwable {
		log.info( "Starting transaction" );
		boolean startedTransaction = false;
		if ( utx.getStatus() != Status.STATUS_ACTIVE ) {
			utx.begin();
			startedTransaction = true;
		}
		Object ret = null;
		try {
			ret = ic.proceed();

			if ( startedTransaction ) {
				log.info( "Committing transaction" );
				utx.commit();
				log.info( "Transaction committed" );
			}
		}
		catch ( Throwable t ) {
			if ( startedTransaction ) {
				utx.rollback();
			}

			throw t;
		}
		return ret;
	}
}


