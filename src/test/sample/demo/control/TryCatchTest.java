package demo.control;

import java.lang.reflect.Method;

import jw.asmsupport.block.control.Finally;
import jw.asmsupport.block.control.Try;
import jw.asmsupport.block.method.common.CommonMethodBody;
import jw.asmsupport.block.method.common.StaticMethodBody;
import jw.asmsupport.block.method.init.InitBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.GlobalVariable;
import jw.asmsupport.definition.variable.LocalVariable;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import demo.CreateMethod;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class TryCatchTest {

	static GlobalVariable out = CreateMethod.out;
	
	@Test
	public void test(){
		main(null);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "demo.TryCatch", null, null);
        creator.createConstructor(null, null, new InitBody(){

			@Override
			public void generateBody(LocalVariable... argus) {
				invokeSuperConstructor();
				runReturn();
			}
			
		}, Opcodes.ACC_PUBLIC);
		
		createTryFinally(creator);
		
		//new TryCatch(creator).createMethod();
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null, 
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
			    new StaticMethodBody(){
					@Override
					public void generateBody(LocalVariable... argus) {
						AClass curCls = getMethodOwner();
						LocalVariable self = createVariable("myMain", getMethodOwner(), false, invokeConstructor(curCls));

						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                        Try Catch Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "tryFinally");
						
						runReturn();
					}
		});
		
		creator.setClassOutPutPath("D:\\TEMP\\Test\\");
    	
		Class<?> example = creator.startup();
		
        try {
			Method main = example.getMethod("main", String[].class);
			main.invoke(example, new Object[]{null});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static void createTryFinally(ClassCreator creator ) {

		creator.createMethod("tryFinally", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void generateBody(LocalVariable... argus) {
						final AClass acls = AClassFactory.getProductClass(Class.class);
						tryDo(new Try(){
							@Override
							public void generateBody() {
								createVariable("stringClass", acls, false, invokeStatic(acls, "forName", Value.value("java.lang.String")));
								runReturn();
							}
						}).finallyThan(new Finally(){
							@Override
							public void generateInsn() {
							    invoke(out, "println", Value.value("hello..."));
							}
							
						});

					}
		        }
		);
	}

}
