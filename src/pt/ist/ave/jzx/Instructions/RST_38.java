package pt.ist.ave.jzx.Instructions;


public class RST_38 extends Instruction {

	public RST_38(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.push(_cpu.getM_pc16());
		_cpu.setM_pc16(0x38);
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
