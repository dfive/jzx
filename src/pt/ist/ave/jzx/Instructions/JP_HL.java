package pt.ist.ave.jzx.Instructions;

public class JP_HL extends Instruction {

	public JP_HL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_pc16(_cpu.hl16());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
