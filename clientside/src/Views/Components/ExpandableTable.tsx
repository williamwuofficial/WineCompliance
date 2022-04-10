import { action, makeObservable, observable } from "mobx";
import { observer } from "mobx-react";
import React, { Key } from "react";
import { ReactComponent as ArrowDownIcon } from '../../Assets/Icons/Arrow-Drop-Down.svg';
import { Button, Table } from "reactstrap";
import '../../css/expandable-table.css'
import WineBreakdownPercentageEntity from "../../Model/WineBreakdownPercentageEntity";
import WineBreakdownEntity from "../../Model/WineBreakdownEntity";

@observer
export class ExpandableTableSection extends React.Component<{ wineBreakdown?: WineBreakdownEntity; }> {
    constructor(props: any) {
        super(props);
        makeObservable(this);
    }

    @observable
    private expand: boolean;

    @action
    private setExpand = (expand: boolean) => {
        this.expand = expand;
    }
    
    render() {
        return (
            <div className={`expandable-table bg-white mt-2`}>
                <Table borderless className={`mb-1 ${this.props.wineBreakdown?.breakDownType}`}>
                    <thead>
                        <tr>
                            <th className="text-capitalize fw-normal">
                                {this.props.wineBreakdown?.breakDownType.replace('-', ' & ')}
                            </th>
                            <th className="text-end fw-normal">
                                Percentage
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.props.wineBreakdown?.breakdown
                            .slice(0, this.expand ? undefined : 5)
                            .map(
                                (percentage: WineBreakdownPercentageEntity) =>
                                    <tr key={percentage.key as Key}
                                        className='fw-light'>
                                        <td>{percentage.key}</td>
                                        <td className="text-end">{percentage.percentage.toFixed(1)}%</td>
                                    </tr>
                            )}
                    </tbody>
                </Table>
                {this.props.wineBreakdown?.breakdown && this.props.wineBreakdown?.breakdown.length > 5
                    && !this.expand
                    && <Button 
                        outline 
                        className="w-100 m-auto mb-1 border-0"
                        onClick={() => { this.setExpand(true) }}>
                        <div className="d-flex justify-content-center">
                            <div className="pe-1">
                                Show more
                            </div>
                            <ArrowDownIcon className="h-100 my-auto"/>
                        </div>
                    </Button>
                }
            </div>
        );
    }
}
