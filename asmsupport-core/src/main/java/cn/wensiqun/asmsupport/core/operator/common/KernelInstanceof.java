/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.common;

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelInstanceof extends AbstractParamOperator {

    private IClass type; 
    private KernelParam obj;
    private boolean byOtherUsed;
    
    protected KernelInstanceof(KernelProgramBlock block, KernelParam obj, IClass type) {
        super(block, Operator.INSTANCE_OF);
        this.obj = obj;
        this.type = type;
    }

    @Override
    protected void verifyArgument() {
        if(obj.getResultType().isPrimitive()){
            throw new ASMSupportException("Incompatible conditional operand types " + obj.getResultType() + " int and " + type);
        }
    }

    @Override
    protected void checkAsArgument() {
        obj.asArgument();
    }

    @Override
    public void execute(MethodExecuteContext context) {
        if(byOtherUsed){
            super.execute(context);
        }else{
            throw new ASMSupportException("the instanceof operator has not been used by other operator.");
        }
    }

    @Override
    protected void doExecute(MethodExecuteContext context) {
        obj.push(context);
        context.getInstructions().instanceOf(type.getType());
    }

    @Override
    public void push(MethodExecuteContext context) {
        this.execute(context);
    }

    @Override
    public IClass getResultType() {
        return getType(boolean.class);
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        getParent().removeChild(this);
    }

}
