package delegate;

import identity.ClassIdentity;
import identity.MethodIdentity;
import identity.SchemeIdentity;

import log.LogService;
import message.Message;
import message.MessageStack;
import utility.MemoryBlock;
import utility.Utils;

public class GenericHandler implements IDelegate
{
	private Delegate innerDelegate = null ;
	
	private ClassIdentity  classIdentity  = null ;	
	private MethodIdentity methodIdentity = null ;
	
	public GenericHandler ( String _call ) throws Exception
	{
		int lastDot = _call.lastIndexOf( '.' ) ;
		
		String fullName = _call.substring( 0  , lastDot ) ;
		String methodName = _call.substring(  ++lastDot , _call.length()  ) ;
		
		classIdentity = new ClassIdentity(  Utils.getClassName ( fullName ) , Utils.getClassNameSpace( fullName )   ) ;
		methodIdentity = new MethodIdentity ( methodName , classIdentity , true ) ;
		
		innerDelegate = new Delegate ( _call ) ;
	}
	

	public GenericHandler ( Object _reference , String _method ) throws Exception
	{
		classIdentity = new ClassIdentity(  Utils.getClassName (_reference ) , Utils.getClassNameSpace( _reference )   ) ;
		methodIdentity = new MethodIdentity (_method , _reference , classIdentity ) ;
		
		innerDelegate = new Delegate ( _reference , _method ) ;
	}
	

	public void invoke ( Object _context , MemoryBlock _memoryBlock , MessageStack _messages  ) throws Exception
	{
		if ( _context == null )
			throw new Exception ( "Context can not be null") ;
		
		if ( _memoryBlock == null )
			throw new Exception( "Memory block can not be null") ;
		
		LogService.getLogger( this ).finer ( methodIdentity.getShortDescription() + " is going to be invoked." ) ;
	
		Object [] arguments =  (_messages == null ) ? new Object [] { _context , _memoryBlock , new MessageStack () } : new Object [] { _context , _memoryBlock , _messages } ;
		innerDelegate.untypedInvoke( arguments ) ;
		
		LogService.getLogger( this ).finer ( methodIdentity.getShortDescription() + " has just been invoked." ) ;
	}
	
	
	public boolean hasReference ()
	{
		return innerDelegate.hasReference() ;
	}
	
	public String getReferenceInfo ()
	{
		return innerDelegate.getReferenceInfo() ;
	}
	
	public String getMethodName ()
	{
		return innerDelegate.getMethodName() ;
	}
	
	public String getClassName ()
	{
		return innerDelegate.getClassName() ;
	}
	
	public boolean hasArguments ()
	{
		return innerDelegate.hasArguments() ;
	}
	
	public boolean needArguments ()
	{
		return innerDelegate.needArguments() ;
	}
	
	public boolean isStatic ()
	{
		return innerDelegate.isStatic() ;
	}
	
	public String getDescription ()
	{
		return innerDelegate.getDescription() ; 
	}
}
