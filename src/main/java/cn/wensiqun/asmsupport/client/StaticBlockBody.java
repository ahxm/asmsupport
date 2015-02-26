package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.standard.method.IStaticBlockBody;

public abstract class StaticBlockBody extends ProgramBlock<ClinitBodyInternal> implements IStaticBlockBody {

	public StaticBlockBody() {
		target = new ClinitBodyInternal() {
			@Override
			public void body() {
				StaticBlockBody.this.body();
			}
		};
	}
	
}