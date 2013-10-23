/**
 * 
 */
package cn.wensiqun.asmsupport.block.method.init;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.method.SuperMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;
import cn.wensiqun.asmsupport.operators.method.SuperConstructorInvoker;
import cn.wensiqun.asmsupport.operators.util.OperatorFactory;
import cn.wensiqun.asmsupport.utils.ModifierUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class InitBody extends SuperMethodBody implements IInitBody {

	@Override
	public MethodInvoker invokeSuperConstructor(Parameterized... arguments) {
    	AClass owner = getMethodOwner();
    	if(ModifierUtils.isEnum(getMethodOwner().getModifiers())){
    		throw new ASMSupportException("Cannot invoke super constructor from enum type " + owner);
    	}
        invokeVerify(owner);

        return OperatorFactory.newOperator(SuperConstructorInvoker.class, 
        		new Class<?>[]{ProgramBlock.class, AClass.class, Parameterized[].class}, 
        		getExecuteBlock(), owner, arguments);
	}
    
    @Override
    public final void generateBody() {
    	generateBody(argments);
    }
    
    public abstract void generateBody(LocalVariable... argus);
}