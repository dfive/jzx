package pt.ist.ave.jzx.Instructions;


public class RST_0 extends Instruction {

	public RST_0(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.push(_cpu.getM_pc16());
		_cpu.setM_pc16(0x0);
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
