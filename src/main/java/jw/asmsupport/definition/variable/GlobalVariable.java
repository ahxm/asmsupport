/**
 * 
 */
package jw.asmsupport.definition.variable;

import java.lang.reflect.Modifier;

import jw.asmsupport.Crementable;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.entity.GlobalVariableEntity;
import jw.asmsupport.entity.VariableEntity;
import jw.asmsupport.operators.AbstractOperator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class GlobalVariable extends MemberVariable implements Crementable{

    private static Log log = LogFactory.getLog(GlobalVariable.class);
    
    private GlobalVariableEntity gve;
    
    /**如果当前全局变量是静态变量，那么staticOwner表示静态变量的所属Class */
    private AClass staticOwner;
    
    /**如果当前全局变量所属与某一个变量中，那么variableOwner表示当前全局变量所属的变量*/
    private IVariable variableOwner;
    
    /**
     * 
     * 
     * 通过Class获取的全局变量
     * @param owner 变量拥有者
     * @param declareClass 变量声明类型
     * @param actuallyClass 变量真实类型
     * @param modifiers 变量的修饰符
     * @param name 变量名
     * 
     */
    public GlobalVariable(AClass owner, AClass declareClass,int modifiers,
            String name) {
        gve = new GlobalVariableEntity(owner, declareClass,modifiers, name);
        staticOwner = owner;
    }
    
    /**
     * 
     * @param owner
     * @param gve
     */
    public GlobalVariable(AClass owner, GlobalVariableEntity gve){
        this.gve = gve;
        staticOwner = owner;
    }
    
    /**
     * 通过Variable获取的全局变量
     * @param var 变量
     * @param varClass 变量类型
     * @param modifiers 变量修饰符
     * @param name 变量名
     */
    public GlobalVariable(IVariable var, AClass declareClass, int modifiers,
            String name) {
        gve = new GlobalVariableEntity(var.getParamterizedType(), declareClass, modifiers, name);
        variableOwner = var;
    }
    
    /**
     * 
     * @param var
     * @param gve
     */
    public GlobalVariable(IVariable var, GlobalVariableEntity gve){
        this.gve = gve;
        variableOwner = var;
    }

    @Override
    public AClass getParamterizedType() {
        return gve.getDeclareClass();
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        //如果是静态
        if(Modifier.isStatic(gve.getModifiers())){
            if(log.isDebugEnabled()){
                log.debug("get field " + gve.getName() + " from class " + gve.getOwner().getName() + " and push to stack!");
            }
            block.getInsnHelper().getStatic(staticOwner.getType(),
                    gve.getName(), gve.getDeclareClass().getType());
        }else{
            if(log.isDebugEnabled()){
                log.debug("get field " + gve.getName() + " from variable " + gve.getName() + " and push to stack!");
            }
            variableOwner.loadToStack(block);
            block.getInsnHelper().getField(gve.getOwner().getType(), gve.getName(), gve.getDeclareClass().getType());
        }
    }
    
    /**
     * staticOwner的get方法
     * 获取当前变量的拥有者
     * @return
     */
    public AClass getStaticOwner() {
        return staticOwner;
    }

    /**
     * variableOwner的get方法
     * 获取当前变量的拥有者
     * @return
     */
    public IVariable getVariableOwner() {
        return variableOwner;
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }

    public GlobalVariableEntity getGlobalVariableEntity(){
        return gve;
    }
    
    @Override
    public VariableEntity getVariableEntity() {
        return gve;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        return getGlobalVariable(gve.getDeclareClass(), name);
    }
    
    @Override
    public String toString() {
        return gve.getName();
    }

}
