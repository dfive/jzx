package pt.ist.ave.jzx.Instructions;

public class PUSH_AF extends Instruction {

	public PUSH_AF(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.storeFlags();
		_cpu.push(_cpu.af16());
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
