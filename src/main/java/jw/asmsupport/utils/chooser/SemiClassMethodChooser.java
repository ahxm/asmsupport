/**
 * 
 */
package jw.asmsupport.utils.chooser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.SemiClass;
import jw.asmsupport.definition.method.Method;
import jw.asmsupport.entity.MethodEntity;
import jw.asmsupport.exception.ASMSupportException;
import jw.asmsupport.utils.AClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class SemiClassMethodChooser extends AbstractMethodChooser {

    private SemiClass methodOwner;
    
    public SemiClassMethodChooser(AClass invoker, SemiClass methodOwner, String name,
            AClass[] argumentTypes) {
        super(invoker, name, argumentTypes);
        this.methodOwner = methodOwner;
    }

    @Override
    public MethodEntity firstPhase() {
/*        TypeTreeNode[] argTypeNodes = new TypeTreeNode[argumentTypes.length];
        for(int i = 0; i<argTypeNodes.length; i++){
            argTypeNodes[i] = translateToTypeTreeRoot(argumentTypes[i]);
        }
        //所有可能的参数
        //List<TypeTreeNode[]> allArgTypes = allPossibleArguments(argTypeNodes);
        allPossibleArguments(argTypeNodes);*/
        MethodEntity foundMe = null;
        //******************************phase 1*************************************
        
        //find in current class
        List<Method> methods = new ArrayList<Method>(methodOwner.getMethods());
        //Collections.copy(methods, methodOwner.getMethods());
        TypeTreeNode[] ttns;

        //所有找到的可能的方法
        List<MethodEntityTypeTreeNodeCombine> foundCombine = new ArrayList<MethodEntityTypeTreeNodeCombine>();
        //参数是否和传入的参数相同
        boolean sameToPass = true;
        MethodEntity mte;
        for(int i=0, length = allArgTypes.size(); i<length; i++){
            ttns = allArgTypes.get(i);
            for(int k=0, mlen = methods.size();  k<mlen; k++){
                mte = methods.get(k).getMethodEntity();
                //如果名字不相同直接跳过
                if(!name.equals(mte.getName())){
                    continue;
                }
                
                AClass[] mtdArgs = mte.getArgClasses();
                //如果参数个数不同直接跳过
                if(mtdArgs.length != ttns.length){
                    continue;
                }
                //如果有一个参数的类型不同直接跳过
                for(int j=0; j<ttns.length; j++){
                    if(!ttns[j].type.equals(mtdArgs[j])){
                        sameToPass = false;
                        continue;
                    }
                }
                
                if(i == 0 && sameToPass){
                    return mte;
                }
                methods.remove(k);
                foundCombine.add(new MethodEntityTypeTreeNodeCombine(mte, allArgTypes.remove(i)));
                length--;
                break;
            }
        }
        
        //find in super class
        
        String[] arguNames = new String[argumentTypes.length];
        
        for(int i=0; i<argumentTypes.length; i++){
            arguNames[i] = "arg" + i;
        }
        
        Class<?> reallyClass = methodOwner.getSuperClass();
        //找到的方法
        MethodEntityTypeTreeNodeCombine[] mettncs = determineMethodInJavaClass(invoker, reallyClass, name, argumentTypes, allArgTypes);
        
        Collections.addAll(foundCombine, mettncs);

        mettncs = foundCombine.toArray(new MethodEntityTypeTreeNodeCombine[foundCombine.size()]);
        
        foundMe = determineMostSpecificMethodEntity(mettncs);
        
        return foundMe;
    }

    @Override
    public MethodEntity secondPhase() {
        List<AClass[]> list = new ArrayList<AClass[]>(AClassUtils.allArgumentWithBoxAndUnBoxCountExceptSelf(argumentTypes));
        AClassUtils.allArgumentWithBoxAndUnBox(argumentTypes, AClassUtils.primitiveFlag(argumentTypes), 0, new AClass[argumentTypes.length], list);
        
        int foundNumber = 0;
        MethodEntity me = null;
        for(AClass[] argsTypes : list){
            SemiClassMethodChooser scmc = new SemiClassMethodChooser(invoker, methodOwner, name, argsTypes);
            //use the first phase's algorithm
            me = scmc.firstPhase();
            if(me != null){
                foundNumber++;
                if(foundNumber > 1){
                    throw new ASMSupportException(" Ambiguous ...............");
                }
            }
        }
        
        return me;
    }

    @Override
    public MethodEntity thirdPhase() {
        List<MethodEntity> applicable = applicableVariableVarifyMethod(invoker, methodOwner, name, argumentTypes);
        List<TypeTreeNode[]> appliNodes = applicableVariableVarifyMethodArgumentsNodes(applicable);
        
        if(appliNodes.size() > 0){
            int mostIndex = mostSpecificIndexForVariableVarify(appliNodes);
            if(mostIndex == -1){
                throw new ASMSupportException(" Ambiguous ...............");
            }else{
                MethodEntity me = applicable.get(mostIndex);
                return me;
            }
        }else{
            return null;
        }
    }

    @Override
    protected MethodEntity foundMethodWithNoArguments() {
        for (Method m : methodOwner.getMethods()) {
            AClass[] actual = m.getMethodEntity().getArgClasses();
            if (m.getMethodEntity().getName().equals(name) && 
                (actual == null ||    
                actual.length == 0)) {
                return m.getMethodEntity();
            }
        }
        
        return foundMethodWithNoArguments(methodOwner.getSuperClass());
    }

}
