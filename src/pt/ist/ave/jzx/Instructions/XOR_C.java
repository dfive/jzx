package pt.ist.ave.jzx.Instructions;

public class XOR_C extends Instruction {

	public XOR_C(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
